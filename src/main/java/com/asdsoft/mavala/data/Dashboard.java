package com.asdsoft.mavala.data;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Dashboard {
    private List<DashboardCard> dashboardCardList;
    private List<DynamicCard> dynamicCardsList;
}
