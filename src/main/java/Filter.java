import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Filter extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] messages = event.getMessage().getContentRaw().split(" ");
        for (String message:messages) {
            if(message.equalsIgnoreCase("fuck")){
                event.getMessage().delete().queue();
                event.getChannel().sendMessage("DON'T SAY LIKE THAT!").queue();
            }
        }
    }
}
