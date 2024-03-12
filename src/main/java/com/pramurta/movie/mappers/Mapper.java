package com.pramurta.movie.mappers;

public interface Mapper<A,B> {
    B mapToDto(A a);

    A mapToEntity(B b);
}
