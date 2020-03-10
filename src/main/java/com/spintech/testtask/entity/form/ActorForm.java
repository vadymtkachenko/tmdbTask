package com.spintech.testtask.entity.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ActorForm extends BaseForm {

    private List<Long> favoriteActorsIds;
}
