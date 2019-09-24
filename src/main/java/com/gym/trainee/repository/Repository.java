package com.gym.trainee.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T1, T2> {

    Optional<T2> get(T1 id);

    T2 add(T2 entity);

    T2 update(T1 id, T2 entity);
}