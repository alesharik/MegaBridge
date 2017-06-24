/*
 *     This file is part of Megabridge.
 *
 *     Megabridge is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Megabridge is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Megabridge.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alesharik.megabridge.discord;

import com.alesharik.megabridge.api.channel.ChannelListener;
import com.alesharik.megabridge.api.message.MessageImpl;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class DiscordMessageListener extends ListenerAdapter {
    private final Map<Channel, ChannelListener> listeners = new ConcurrentHashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.isFromType(ChannelType.PRIVATE))
            return;

        if(listeners.containsKey(event.getTextChannel()))
            listeners.get(event.getTextChannel()).listenMessage(new MessageImpl(event.getMessage().getAuthor().getName(), event.getMessage().getContent()));
    }

    public void addListener(Channel channel, ChannelListener listener) {
        listeners.put(channel, listener);
    }

    public void removeListener(Channel channel, ChannelListener listener) {
        listeners.remove(channel, listener);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof DiscordMessageListener)) return false;

        DiscordMessageListener that = (DiscordMessageListener) o;

        return listeners.equals(that.listeners);
    }

    @Override
    public int hashCode() {
        return listeners.hashCode();
    }
}