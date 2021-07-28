package commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Image extends ListenerAdapter {

    String name, arguments;

    public Image(){
        this.name = "image";
        this.arguments = "[width] [height] [image-url] [degrees to rotate(optional)]";
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {


    }
}
