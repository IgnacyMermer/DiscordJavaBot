package events;

import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CategoryEvent extends ListenerAdapter {

    public void onCategoryCreate(CategoryCreateEvent event){
        event.getGuild().getDefaultChannel().sendMessage("someone just create an event").queue();
    }
}
