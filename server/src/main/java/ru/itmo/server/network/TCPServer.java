package ru.itmo.server.network;

import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.CommandNotFoundResponse;
import ru.itmo.general.network.responses.Response;
import ru.itmo.server.managers.CommandManager;
import ru.itmo.server.managers.collections.TicketCollectionManager;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCPServer {
    private final int port;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private final CommandManager commandManager;
    private static final Logger logger = LoggerFactory.getLogger(TCPServer.class);

    public TCPServer(int port, TicketCollectionManager ticketCollectionManager) {
        this.port = port;
        this.commandManager = new CommandManager(ticketCollectionManager, this);
    }

    public void start() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            logger.info("Сервер запущен на порту {}", port);

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    int readyChannels = selector.select();
                    if (readyChannels == 0) continue;

                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        keyIterator.remove();

                        if (!key.isValid()) continue;

                        if (key.isAcceptable()) {
                            acceptConnection();
                        } else if (key.isReadable()) {
                            readRequest(key);
                        }
                    }
                } catch (IOException e) {
                    if (!(e instanceof ClosedChannelException)) {
                        System.err.println("Ошибка при работе с селектором: " + e.getMessage());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка при запуске сервера", e);
        } finally {
            try {
                stop();
            } catch (IOException e) {
                logger.error("Ошибка при остановке сервера", e);
            }
        }
    }


    public void stop() throws IOException {
        if (serverSocketChannel != null) {
            serverSocketChannel.close();
        }
    }

    private void acceptConnection() throws IOException {
        SocketChannel clientSocketChannel = serverSocketChannel.accept();
        clientSocketChannel.configureBlocking(false);
        clientSocketChannel.register(selector, SelectionKey.OP_READ);
        logger.info("Новое подключение: {}", clientSocketChannel.getRemoteAddress());
    }

    private void readRequest(SelectionKey key) {
        SocketChannel clientSocketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(4096); // Увеличенный размер буфера
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int bytesRead;
        try {
            logger.debug("Чтение запроса от {}", clientSocketChannel.getRemoteAddress());
            while ((bytesRead = clientSocketChannel.read(buffer)) > 0) {
                buffer.flip();
                byteArrayOutputStream.write(buffer.array(), 0, buffer.limit());
                buffer.clear();
            }
            if (bytesRead == -1) {
                key.cancel();
                clientSocketChannel.close();
                System.out.println("Клиент отключился: " + clientSocketChannel);
                return;
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении данных: " + e.getMessage());
            key.cancel();
            try {
                clientSocketChannel.close();
            } catch (IOException ce) {
                System.err.println("Ошибка при закрытии канала: " + ce.getMessage());
            }
            return;
        }

        try {
            byte[] requestBytes = byteArrayOutputStream.toByteArray();
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(requestBytes));
            Request request = (Request) objectInputStream.readObject();
            Response response = processRequest(request);
            sendResponse(clientSocketChannel, response);
        } catch (Exception e) {
            System.err.println("Ошибка при обработке запроса: " + e.getMessage());
            Response response = new CommandNotFoundResponse("Неверный запрос");
            sendResponse(clientSocketChannel, response);
        }
    }


    private void sendResponse(SocketChannel clientSocketChannel, Response response) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                logger.debug("Отправка ответа клиенту {}", clientSocketChannel.getRemoteAddress());
                objectOutputStream.writeObject(response);
                objectOutputStream.flush();
            } catch (IOException e) {
                System.err.println("Ошибка при сериализации ответа: " + e.getMessage());
                throw e;
            }

            byte[] responseBytes = byteArrayOutputStream.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(responseBytes);
            while (buffer.hasRemaining()) {
                try {
                    clientSocketChannel.write(buffer);
                } catch (IOException e) {
                    System.err.println("Ошибка при отправке данных клиенту: " + e.getMessage());
                    throw e;
                }
            }
        } catch (IOException e) {
            // Здесь можно добавить дополнительную логику, например, попытку переотправить ответ или закрыть соединение
            System.err.println("Неустранимая ошибка при отправке ответа: " + e.getMessage());
        }
    }


    private Response processRequest(Request request) {
        Response response;
        try {
            response = commandManager.handle(request);
            if ("exit".equals(request.getCommand())) {
                logger.info("Получен запрос на завершение работы сервера.");
                try {
                    stop();
                } catch (IOException e) {
                    logger.error("Ошибка при остановке сервера", e);
                }
                System.exit(0);
            }
        } catch (Exception e) {
            response = new CommandNotFoundResponse(request.getCommand());
        }
        return response;
    }
}
