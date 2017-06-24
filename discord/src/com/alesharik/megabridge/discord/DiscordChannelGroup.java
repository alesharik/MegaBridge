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
import com.alesharik.megabridge.api.channel.ChannelGroup;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.core.entities.Guild;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class DiscordChannelGroup implements ChannelGroup {
    private final Guild guild;
    private final DiscordMessageListener listener;

    @Override
    public String getName() {
        return guild.getName();
    }

    @Override
    public List<Channel> getChannels() {
        return guild.getTextChannels().stream()
                .map(textChannel -> new DiscordChannel(textChannel, listener))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
