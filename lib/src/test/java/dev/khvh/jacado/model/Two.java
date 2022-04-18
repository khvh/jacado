package dev.khvh.jacado.model;

import dev.khvh.jacado.Model;
import dev.khvh.jacado.data.Document;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Document(name = "twos")
public class Two extends Model {
}
