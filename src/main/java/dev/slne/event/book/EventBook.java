package dev.slne.event.book;

import java.util.ArrayList;
import java.util.Collection;

import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;

public abstract class EventBook {

    private Component title;
    private Component author;

    public EventBook(Component title, Component author) {
        this.title = title;
        this.author = author;
    }

    public abstract Collection<EventBookPage> getBookPages();

    public Book buildBook() {
        Collection<Component> pageComponents = new ArrayList<>();

        for (EventBookPage bookPage : getBookPages()) {
            pageComponents.add(bookPage.getComponent());
        }

        return Book.book(title, author, pageComponents);
    }

    public Component getTitle() {
        return title;
    }

    public Component getAuthor() {
        return author;
    }

}
