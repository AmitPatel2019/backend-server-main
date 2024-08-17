package com.asdsoft.mavala.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PodcastFilters {
    private List<String> supportedFilters;

    public PodcastFilters(String[] filters) {
        supportedFilters = new ArrayList<>();
        supportedFilters.addAll(Arrays.asList(filters));
    }
}
