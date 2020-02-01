package iot.lviv.ua.Repository;

import iot.lviv.ua.domain.OrderPassage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPassageRepository extends JpaRepository<OrderPassage, Long> {
}
