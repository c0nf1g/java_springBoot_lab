package iot.lviv.ua.Repository;

import iot.lviv.ua.domain.TicketEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketEventRepository extends JpaRepository<TicketEvent, Long> {
}
