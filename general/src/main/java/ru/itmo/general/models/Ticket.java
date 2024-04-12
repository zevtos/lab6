package ru.itmo.general.models;

import ru.itmo.general.utility.base.Element;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Класс, представляющий объект билета.
 * @author zevtos
 */
public class Ticket extends Element {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double price; //Значение поля должно быть больше 0
    private Long discount; //Поле может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 100
    private String comment; //Поле может быть null
    private TicketType type; //Поле может быть null
    private Person person; //Поле не может быть null

    public Ticket(Integer nextId, String name, Coordinates coordinates, ZonedDateTime creationDate, double price, Long discount, String comment, TicketType type, Person person) {
        this.id = nextId;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.discount = discount;
        this.comment = comment;
        this.type = type;
        this.person = person;
    }
    public Ticket(Integer nextId, String name, Coordinates coordinates, double price, Long discount, String comment, TicketType type, Person person) {
        this(nextId, name, coordinates, ZonedDateTime.now(), price, discount, comment, type, person);
    }
    @Override
    public String toString() {
        return "Ticket{" +
                "\n\tid=" + id +
                "\n\tname='" + name + '\'' +
                "\n\tcoordinates=" + coordinates +
                "\n\tcreationDate='" + creationDate.format(DateTimeFormatter.ISO_DATE_TIME) + '\'' +
                "\n\tprice=" + price +
                "\n\tdiscount=" + (discount == null ? "null" : discount) +
                "\n\tcomment='" + (comment == null ? "null" : comment) + '\'' +
                "\n\tticketType='" + (type == null ? "null" : type) + '\'' +
                "\n\t" + (person == null ? "null" : person.toString()) +
                "\n}";
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Проверяет, является ли билет валидным.
     * @return true, если билет валиден, иначе false.
     */
    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        if (creationDate == null) return false;
        if (price <= 0) return false;
        if (discount != null && (discount <= 0 || discount > 100)) return false;
        return person != null && person.validate();
    }
    public boolean validateClient() {
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        if (creationDate == null) return false;
        if (price <= 0) return false;
        if (discount != null && (discount <= 0 || discount > 100)) return false;
        return person != null && person.validate();
    }
    @Override
    public int compareTo(Element element) {
        return Integer.compare(this.getId(), element.getId());
    }
    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public double getPrice() {
        return price;
    }

    public Long getDiscount() {
        return discount;
    }

    public String getComment() {
        return comment;
    }

    public TicketType getType() {
        return type;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket that = (Ticket) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate, coordinates, price, discount, comment, type, person);
    }
    public void update(Ticket Ticket) {
        this.name = Ticket.name;
        this.coordinates = Ticket.coordinates;
        this.creationDate = Ticket.creationDate;
        this.price = Ticket.price;
        this.discount = Ticket.discount;
        this.comment = Ticket.comment;
        this.type = Ticket.type;
        this.person = Ticket.person;
    }
}
