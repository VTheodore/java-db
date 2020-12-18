package alararestaurant.service;

import alararestaurant.domain.entities.Position;

public interface PositionService {
    Position getPositionByName(String name);

    Position save(String positionName);
}
