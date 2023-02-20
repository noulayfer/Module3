package com.fedorenko.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class GroupCountDTO {
    private String groupName;
    private int studentsCount;
}
