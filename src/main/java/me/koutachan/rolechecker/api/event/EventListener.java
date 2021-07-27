package me.koutachan.rolechecker.api.event;

/*
 初めてなので参考（パクリ）:
 https://castever.wordpress.com/2008/07/31/how-to-create-your-own-events-in-java/
 https://en.wikipedia.org/wiki/Observer_pattern#Java
 */

import net.dv8tion.jda.api.EmbedBuilder;

import java.util.*;

public class EventListener {
    public interface Listener {
        void Event(Event event);
    }

    private static final List<Listener> observers = new ArrayList<>();

    public static class Event {
        private final String name;
        private final String discordID;
        private EmbedBuilder embedBuilder;
        private final boolean isSuccess;
        private final reasonEnum reasonEnum;

        public Event(String name, String discordID, EmbedBuilder embedBuilder, boolean isSuccess, reasonEnum reasonEnum) {
            this.name = name;
            this.discordID = discordID;
            this.embedBuilder = embedBuilder;
            this.isSuccess = isSuccess;
            this.reasonEnum = reasonEnum;
        }

        public String getName() {
            return this.name;
        }

        public String getDiscordID() {
            return this.discordID;
        }

        public EmbedBuilder getEmbedBuilder() {
            return this.embedBuilder;
        }

        public void setEmbedBuilder(EmbedBuilder embedBuilder) {
            this.embedBuilder = embedBuilder;
        }

        public boolean isSuccess() {
            return this.isSuccess;
        }

        public reasonEnum getReason() {
            return this.reasonEnum;
        }
    }

    public enum reasonEnum {
        JOIN,
        REMOVE,
        FORCEJOIN,
        FORCEREMOVE,
    }

    public void addListener(Listener listener) {
        observers.add(listener);
    }

    public Event request(String name, String discordID, EmbedBuilder embedBuilder, boolean isSuccess, reasonEnum reasonEnum) {
        Event event = new Event(name, discordID, embedBuilder, isSuccess, reasonEnum);
        EventListener.observers.forEach(observers -> observers.Event(event));
        return event;
    }
}
