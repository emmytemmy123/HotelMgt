package fcmb.com.good.services.events;


import fcmb.com.good.model.dto.response.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.eventsRequest.EventOrderRequest;
import fcmb.com.good.model.dto.request.eventsRequest.EventOrderRequest2;
import fcmb.com.good.model.dto.response.eventsResponse.EventOrderResponse;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.entity.Events.EventOrder;
import fcmb.com.good.model.entity.Events.Events;
import fcmb.com.good.model.entity.activityLog.ActivityLog;
import fcmb.com.good.model.entity.user.Users;
import fcmb.com.good.repo.activityLog.ActivityLogRepository;
import fcmb.com.good.repo.events.EventOrderRepository;
import fcmb.com.good.repo.events.EventsCategoryRepository;
import fcmb.com.good.repo.events.EventsRepository;
import fcmb.com.good.repo.user.UsersRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventOrderServiceImpl implements EventOrderService {

    private final EventsRepository eventsRepository;
    private final EventOrderRepository eventOrderRepository;
    private final UsersRepository usersRepository;
    private final EventsCategoryRepository eventsCategoryRepository;
    private final ActivityLogRepository activityLogRepository;



    @Override
    /**
     * @Validate and Find the list of all EventOrders
     * @Validate if the List of EventOrders is empty otherwise return record not found
     * @return the list of EventOrders and a Success Message
     * * */
    public ApiResponse<List<EventOrderResponse>> getListOfEventOrder(int page, int size) {

        List<EventOrder> eventOrderList = eventOrderRepository.findAll(PageRequest.of(page,size)).toList();
        if(eventOrderList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(eventOrderList, EventOrderResponse.class));

    }

    @Override
    /**
     * @Validate that user exists otherwise return record not found
     * @Validate that event exists otherwise return record not found
     * Create the eventOrder definition and save
     * @return success message* *
     * * */
    public ApiResponse<String> addEventOrders(EventOrderRequest request) {

        Users existingUser  = usersRepository.findByUuid(request.getCreatedById())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

        Events existingEvent = eventsRepository.findByUuid(request.getEventId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));

    EventOrder eventOrder = new EventOrder();

    eventOrder.setEventName(existingEvent.getName());
    eventOrder.setOrderBy(request.getOrderBy());
    eventOrder.setPhone(request.getPhone());
    eventOrder.setDescription(request.getDescription());
    eventOrder.setCapacity(existingEvent.getCapacity());
    eventOrder.setStatus("booked");
    eventOrder.setCategory(existingEvent.getCategory());
    eventOrder.setPrice(existingEvent.getPrice());
    eventOrder.setEventDay(request.getEventDay());
    eventOrder.setEvents(existingEvent);
    eventOrder.setCreatedBy(existingUser);

    eventOrderRepository.save(eventOrder);

        existingEvent.setStatus("booked");

        eventsRepository.save(existingEvent);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName("eventOrder");
        activityLog.setCategory("add");
        activityLog.setDescription("this is a eventOrder add log");
        activityLog.setPerformedBy(existingUser.getName());
        activityLog.setPerformedDate(LocalDateTime.now());

        activityLogRepository.save(activityLog);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Added successfully");

    }


    @Override
    /**
     * @Validating and Finding the list of eventOrderOptional by uuid
     * @Validate if the List of eventOrderOptional is empty otherwise return record not found
     * Create the eventOrder definition and get the assetsOptional by uuid
     * @return the list of eventOrder and a Success Message
     * * */
    public ApiResponse<EventOrderResponse> getEventOrderById(UUID eventOrderId) {

        Optional<EventOrder> eventOrderOptional = eventOrderRepository.findByUuid(eventOrderId);
        if(eventOrderOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        EventOrder eventOrder = eventOrderOptional.get();
        return new ApiResponse<EventOrderResponse>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(eventOrderOptional, EventOrderResponse.class));


    }



    /**
     * @validating eventOrderOptional by uuid
     * @Validate if the List of eventOrderOptional is empty otherwise return record not found
     * @return eventOrderOptional
     * * */
    private EventOrder validateEventOrder(UUID uuid){
        Optional<EventOrder> eventOrderOptional = eventOrderRepository.findByUuid(uuid);
        if(eventOrderOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return eventOrderOptional.get();
    }



    @Override
    /**
     * @validating eventOrderOptional by uuid
     * @Validate if the List of eventOrderOptional is empty otherwise return record not found
     * Create the eventOrderOptional definition and update
     * @return a Success Message
     * * */
    public ApiResponse<String> updateEventOrder(UUID eventOrderId, EventOrderRequest2 request) {

        EventOrder eventOrder = validateEventOrder(eventOrderId);

        if(request.getOrderBy() != null){
            eventOrder.setOrderBy(request.getOrderBy());
        }

        if(request.getPhone() !=null){
            eventOrder.setPhone(request.getPhone());
        }

        if(request.getDescription() !=null){
            eventOrder.setDescription(request.getDescription());
        }

        if(request.getStatus() != null){
            eventOrder.setStatus("paid");
        }

        eventOrder.setPaymentStatus("paid");

        eventOrderRepository.save(eventOrder);

        Events events = eventOrder.getEvents();
        events.setStatus("paid");

        eventsRepository.save(events);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName("eventOrder");
        activityLog.setCategory("update");
        activityLog.setDescription("this is a eventOrder update log");
//      activityLog.setPerformedBy();
        activityLog.setPerformedDate(LocalDateTime.now());

        activityLogRepository.save(activityLog);

        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record updated successfully");


    }





}
