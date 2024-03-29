package com.fedorenko.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class GroupGradeDTO {
    private String name;
    private float value;
}
