package dev.alphacentauri.stafftools.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    private final String BOT_TOKEN = "OTI3NjQ4NTA3MTE5MTYxMzY0.YdNR_g.YaOqc7DHmXJXE2rQvVPg1fjqw08";
    private JDA jda;

    public DiscordBot() {
        try {
            this.jda = JDABuilder.createDefault(this.BOT_TOKEN)

                    .setActivity(Activity.playing("mc.dynamicbyte.de"))
                    .setMemberCachePolicy(MemberCachePolicy.ALL)

                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .enableIntents(GatewayIntent.GUILD_BANS)
                    .enableIntents(GatewayIntent.GUILD_EMOJIS)
                    .enableIntents(GatewayIntent.GUILD_WEBHOOKS)
                    .enableIntents(GatewayIntent.GUILD_INVITES)
                    .enableIntents(GatewayIntent.GUILD_VOICE_STATES)
                    .enableIntents(GatewayIntent.GUILD_PRESENCES)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES)
                    .enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS)
                    .enableIntents(GatewayIntent.GUILD_MESSAGE_TYPING)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGES)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGE_REACTIONS)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGE_TYPING)

                    .enableCache(CacheFlag.MEMBER_OVERRIDES)

                    .build();
        } catch (LoginException exception) {
            exception.printStackTrace();
        }
    }

    public JDA getJda() {
        return jda;
    }

}
