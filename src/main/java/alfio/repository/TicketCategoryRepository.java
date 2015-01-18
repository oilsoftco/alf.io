/**
 * This file is part of alf.io.
 *
 * alf.io is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * alf.io is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with alf.io.  If not, see <http://www.gnu.org/licenses/>.
 */
package alfio.repository;

import alfio.datamapper.AutoGeneratedKey;
import alfio.datamapper.Bind;
import alfio.datamapper.Query;
import alfio.datamapper.QueryRepository;
import alfio.model.TicketCategory;
import org.apache.commons.lang3.tuple.Pair;

import java.time.ZonedDateTime;
import java.util.List;

@QueryRepository
public interface TicketCategoryRepository {

    @Query("insert into ticket_category(inception, expiration, name, description, max_tickets, price_cts, access_restricted, tc_status, event_id) " +
            "values(:inception, :expiration, :name, :description, :max_tickets, :price, :accessRestricted, 'ACTIVE', :eventId)")
    @AutoGeneratedKey("id")
    Pair<Integer, Integer> insert(@Bind("inception") ZonedDateTime inception,
                                  @Bind("expiration") ZonedDateTime expiration,
                                  @Bind("name") String name,
                                  @Bind("description") String description,
                                  @Bind("max_tickets") int maxTickets,
                                  @Bind("price") int price,
                                  @Bind("accessRestricted") boolean accessRestricted,
                                  @Bind("eventId") int eventId);

    @Query("select * from ticket_category where id = :id and event_id = :eventId and tc_status = 'ACTIVE'")
    TicketCategory getById(@Bind("id") int id, @Bind("eventId") int eventId);

    @Query("select * from ticket_category where event_id = :eventId  and tc_status = 'ACTIVE' order by inception asc, expiration asc, id asc")
    List<TicketCategory> findAllTicketCategories(@Bind("eventId") int eventId);
    
    @Query("select * from ticket_category where event_id = :eventId")
    List<TicketCategory> findByEventId(@Bind("eventId") int eventId);
    
    @Query("select count(*) from ticket_category where event_id = :eventId and access_restricted = true")
    Integer countAccessRestrictedRepositoryByEventId(@Bind("eventId") int eventId);

    @Query("update ticket_category set name = :name, inception = :inception, expiration = :expiration, description = :description, " +
            "max_tickets = :max_tickets, price_cts = :price, access_restricted = :accessRestricted where id = :id")
    int update(@Bind("id") int id,
               @Bind("name") String name,
               @Bind("inception") ZonedDateTime inception,
               @Bind("expiration") ZonedDateTime expiration,
               @Bind("max_tickets") int maxTickets,
               @Bind("price") int price,
               @Bind("accessRestricted") boolean accessRestricted,
               @Bind("description") String description);

    @Query("update ticket_category set max_tickets = :max_tickets where id = :id")
    int updateSeatsAvailability(@Bind("id") int id, @Bind("max_tickets") int maxTickets);

    @Query("update ticket_category set tc_status = 'NOT_ACTIVE' where id = :id")
    int deactivate(@Bind("id") int id);

    @Query("update ticket_category set inception = :inception, expiration = :expiration where id = :id")
    int fixDates(@Bind("id") int id, @Bind("inception") ZonedDateTime inception, @Bind("expiration") ZonedDateTime expiration);
}
