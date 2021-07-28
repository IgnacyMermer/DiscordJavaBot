package commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InviteCommands extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){

        String[] messages = event.getMessage().getContentRaw().split(" ");

        if(messages[0].equalsIgnoreCase("$invite")){
            event.getChannel().sendMessage("You want to send invite to "+messages[1]+"?\n" +
                    event.getMember().getUser().getName()+" give him this link "+
                    event.getChannel().createInvite().complete().getUrl()).queue();
        }
    }

}
