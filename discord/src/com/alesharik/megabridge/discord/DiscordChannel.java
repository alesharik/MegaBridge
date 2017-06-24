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

import com.alesharik.megabridge.api.channel.Channel;
import com.alesharik.megabridge.api.channel.ChannelListener;
import com.alesharik.megabridge.api.message.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import sun.misc.Cleaner;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class DiscordChannel implements Channel {
    private final TextChannel textChannel;
    private final DiscordMessageListener listener;
    private final List<ChannelListener> listeners = new CopyOnWriteArrayList<>();

    public DiscordChannel(TextChannel textChannel, DiscordMessageListener listener) {
        this.textChannel = textChannel;
        this.listener = listener;
        Cleaner.create(this, () -> listeners.forEach(l -> listener.removeListener(textChannel, l)));
    }

    @Override
    public void sendMessage(Message msg) {
        textChannel.sendMessage(msg.getMessage());
    }

    @Override
    public void addListener(ChannelListener listener) {
        listeners.add(listener);
        this.listener.addListener(textChannel, listener);
    }

    @Override
    public void removeListener(ChannelListener listener) {
        listeners.remove(listener);
        this.listener.removeListener(textChannel, listener);
    }
}
