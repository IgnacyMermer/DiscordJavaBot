import commands.Calculate;
import commands.InviteCommands;
import commands.Weather;
import events.CategoryEvent;
import events.HelloEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {

    public static void main(String[] args){

        try {
            //http://api.openweathermap.org/data/2.5/weather?q=Wielu%C5%84&appid=0d6fb47fb7503f7e9cf432af3812b682

            JDA jda = JDABuilder.createDefault("XXXXXXXXXXXXXXXXXXX").build();

            jda.addEventListener(new HelloEvent());
            jda.addEventListener(new CategoryEvent());
            jda.addEventListener(new Calculate());
            jda.addEventListener(new InviteCommands());
            jda.addEventListener(new Filter());
            jda.addEventListener(new Weather());


        }
        catch (Exception ex){
            ex.getMessage();
        }
    }



}
