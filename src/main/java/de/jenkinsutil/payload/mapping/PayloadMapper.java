package de.jenkinsutil.payload.mapping;

public interface PayloadMapper<I,T> {

    I convert(T input);
}
