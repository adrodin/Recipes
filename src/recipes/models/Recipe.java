package recipes.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="recipes")
public class Recipe {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "name must not be blank")
    private String name;

    @NotBlank(message = "recipe must have category")
    private String category;

    @NotBlank(message = "description must not be blank")
    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    @NotEmpty(message = "recipe must have at least 1 ingredient")
    @ElementCollection
    private List<String> ingredients = new ArrayList<>();

    @NotEmpty(message = "recipe must have at least 1 direction")
    @ElementCollection
    private List<String> directions = new ArrayList<>();

    @JsonIgnore
    private String owner;
}
