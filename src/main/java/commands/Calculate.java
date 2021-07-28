package commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Calculate extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){

        String[] message = event.getMessage().getContentRaw().split(" ");

        try {
            if (message[0].equalsIgnoreCase("!add")) {
                event.getChannel().sendMessage(Integer.toString(Integer.parseInt(message[1]) +
                        Integer.parseInt(message[2]))).queue();
            } else if (message[0].equalsIgnoreCase("!sub")) {
                event.getChannel().sendMessage(Integer.toString(Integer.parseInt(message[1]) -
                        Integer.parseInt(message[2]))).queue();
            } else if (message[0].equalsIgnoreCase("!div")) {
                event.getChannel().sendMessage(Integer.toString(Integer.parseInt(message[1]) /
                        Integer.parseInt(message[2]))).queue();
            } else if (message[0].equalsIgnoreCase("!mult")) {
                event.getChannel().sendMessage(Integer.toString(Integer.parseInt(message[1]) *
                        Integer.parseInt(message[2]))).queue();
            }
        }
        catch (Exception e){
            System.out.println(e.getStackTrace());
        }
    }

}
