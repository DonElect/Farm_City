package com.farmCity.farm_city_be.events;

import com.farmCity.farm_city_be.models.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private UserEntity user;

    public RegistrationCompleteEvent(UserEntity user) {
        super(user);
        this.user = user;
    }
}
