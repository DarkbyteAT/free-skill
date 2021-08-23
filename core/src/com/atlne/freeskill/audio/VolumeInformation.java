package com.atlne.freeskill.audio;

import lombok.Data;

@Data
public class VolumeInformation {
    private float masterVolume = 1;
    private float musicVolume = 1;
    private float soundEffectVolume = 1;
}
