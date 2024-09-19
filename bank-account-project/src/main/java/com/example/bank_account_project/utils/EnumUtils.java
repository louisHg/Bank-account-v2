package com.example.bank_account_project.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class EnumUtils {

    @Getter
    public enum Enum {
        ;
        @Getter
        public enum TAILLE_FORM {
            NORMAL(255), LONG(2000);

            private final Integer id;

            TAILLE_FORM(final Integer id) { this.id = id; }

            public static TAILLE_FORM get(final Integer id){
                return Arrays.stream(TAILLE_FORM.values()).filter(e -> e.getId().equals(id)).findFirst().orElse(null);
            }
        }
    }

    private Short  id;

    private String libelle;

}
