package ru.netology.cloudservicediplom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RenameFileDto {

  @JsonProperty("filename")
  private String newName;
}
