package com.app.norway.domain.user;

import java.util.ArrayList;
import java.util.UUID;

public record UserFavoritesDTO(ArrayList<UUID> favorites){
}
