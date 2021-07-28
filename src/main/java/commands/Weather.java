package commands;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Weather extends ListenerAdapter {

    private static HttpURLConnection connection;

    BufferedReader reader;
    StringBuffer buffer;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        buffer = new StringBuffer();

        String[] messages = event.getMessage().getContentRaw().split(" ");
        String[] weatherTranslations = new String[]{"pogoda", "weather"};
        boolean temp=false;

        for(String translation : weatherTranslations){
            if(messages[0].equalsIgnoreCase(translation)){
                temp=true;
                break;
            }
        }

        if(temp){
            if((messages[1].length()==5||messages[1].length()==6)&&messages[1].charAt(0)>='0'&&messages[1].charAt(0)<='9'){
                String postalCode = messages[1];
                System.out.println("postal code: "+postalCode);

            }
            else{
                String city = messages[1];
                boolean hasStringNoLetter=false;
                for(char a : city.toCharArray()){
                    if(!Character.isAlphabetic(a)){
                        hasStringNoLetter=true;
                        break;
                    }
                }
                if(hasStringNoLetter){
                    event.getMessage().getChannel().sendMessage("The name of the city is incorrect!").queue();
                }
                else {
                    try {
                        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+city+
                                "&appid=XXXXXXXXXXXXXXXXXXXX");

                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(5000);
                        connection.setReadTimeout(5000);

                        int status = connection.getResponseCode();

                        if(status>299){
                            event.getMessage().getChannel().sendMessage("Nie możemy odczytać pogody");
                        }
                        else{

                            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                            while (reader.ready()){
                                buffer.append(reader.readLine());
                            }
                            String jsonInString = buffer.toString();

                            System.out.println(jsonInString);
                            Gson gson = new Gson();


                            String descriptionWeather = getValueFromJSONString(jsonInString, "description",
                                    String.class);

                            double temperature = Math.round((Double.parseDouble(
                                    getValueFromJSONString(jsonInString, "temp", Double.class)
                            )-273.15)*100);
                            temperature/=100;

                            double feels_like = Math.round((Double.parseDouble(
                                    getValueFromJSONString(jsonInString, "feels_like", Double.class)
                            )-273.15)*100);
                            feels_like/=100;

                            double temp_max = Math.round((Double.parseDouble(
                                    getValueFromJSONString(jsonInString, "temp_max", Double.class)
                            )-273.15)*100);
                            temp_max/=100;

                            double pressure = Double.parseDouble(
                                    getValueFromJSONString(jsonInString, "pressure", Double.class)
                            );

                            double speed = Double.parseDouble(
                                    getValueFromJSONString(jsonInString, "speed", Double.class)
                            );

                            MessageChannel channel = event.getMessage().getChannel();
                            channel.sendMessage("Pressure:\t"+pressure+" hPa\n"
                                    +"Speed wind:\t"+speed+" m/s\n"
                                    +"Temperature:\t"+temperature+" ^C\n"
                                    +"Feels like:\t"+feels_like+" ^C").queue();

                            System.out.println("Description:\t"+descriptionWeather);
                            System.out.println(gson.fromJson(buffer.toString(), Map.class).toString());
                            Map<String, Object> map = gson.fromJson(buffer.toString(), Map.class);
                            System.out.println(map);

                            /*JsonElement root = new JsonParser().parse(buffer.toString());
                            double value = root.getAsJsonObject().get("coord").getAsJsonObject().get("lon").getAsDouble();
                            System.out.println(value);
                            System.out.println(root.getAsJsonObject().toString());*/
                        }

                    }
                    catch (Exception ex){
                        System.out.println(ex.getMessage());
                    }
                    finally {
                        connection.disconnect();
                    }
                }

            }
        }
    }

    String getValueFromJSONString(String json, String word, Class type){


        StringBuilder weather = new StringBuilder();
        int index = json.indexOf(word);
        while (json.charAt(index-1)!=':'){
            index++;
        }

        //to skip \" character at the beginning of description string
        if(type==String.class)index++;


        while (json.charAt(index) != (type==String.class?'\"':',')) {
            weather.append(json.charAt(index));
            index++;
        }

        return weather.toString();

    }
}
