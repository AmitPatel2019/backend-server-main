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
public class LangList {
    private List<String> supportedLanList;

    public LangList(String[] langList) {
        supportedLanList = new ArrayList<>();
        supportedLanList.addAll(Arrays.asList(langList));
    }
}
