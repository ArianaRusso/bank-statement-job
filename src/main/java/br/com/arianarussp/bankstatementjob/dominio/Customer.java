package br.com.arianarussp.bankstatementjob.dominio;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Customer {
    private String firstName;
    private String lastName;
    private String document;
    private String address;

}
