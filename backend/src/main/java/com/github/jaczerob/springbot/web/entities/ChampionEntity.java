package com.github.jaczerob.springbot.web.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.github.jaczerob.springbot.web.utils.Embeddable;
import com.merakianalytics.orianna.types.core.staticdata.Champion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="champions")
public class ChampionEntity implements Embeddable {
    @Id
    private int id;
    private String name;
    private String imageURL;

    @OneToMany(mappedBy="champion")
    private List<ChampionMasteryEntity> championMasteries;

    public ChampionEntity(Champion champion) {
        this.id = champion.getId();
        this.name = champion.getName();
        this.imageURL = champion.getImage().getURL();
        this.championMasteries = new ArrayList<>();
    }

    public MessageEmbed toEmbed() {
        return new EmbedBuilder()
            .setImage(this.getImageURL())
            .setTitle(this.getName())
            .build();
    }
}
