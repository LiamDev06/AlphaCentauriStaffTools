package dev.alphacentauri.stafftools.discord;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;

public class DiscordWebhook {

    private final WebhookClient client;

    public DiscordWebhook() {
        WebhookClientBuilder webhook = new WebhookClientBuilder("https://discord.com/api/webhooks/927998625936736306/9yEsuO5_7_77mbiaiMp7QPCo-J9q33Us2FXvUGCycd21_SFNQfrn0dTVjY__JKxhisZt"); // or id, token
        webhook.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("Hello");
            thread.setDaemon(true);
            return thread;
        });
        webhook.setWait(true);
        this.client = webhook.build();
    }

    public WebhookClient getClient() {
        return client;
    }
}
