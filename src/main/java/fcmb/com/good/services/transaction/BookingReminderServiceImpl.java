package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.BookingReminderRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.BookingReminderResponse;
import fcmb.com.good.model.entity.transaction.BookingReminder;
import fcmb.com.good.repo.transaction.BookingReminderRepository;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingReminderServiceImpl implements BookingReminderService {
    private  final BookingReminderRepository bookingReminderRepository;

    @Override
    public ApiResponse<List<BookingReminderResponse>> getListOfBookingReminder(int page, int size) {
        List<BookingReminder> bookingReminderList = bookingReminderRepository.findAll(PageRequest.of(page,size)).toList();
        if(bookingReminderList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(bookingReminderList, BookingReminderResponse.class));
    }

    @Override
    public ApiResponse<String> addBookingReminder(@RequestBody BookingReminderRequest request) {
        BookingReminder bookingReminder = Mapper.convertObject(request,BookingReminder.class);
        bookingReminderRepository.save(bookingReminder);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record added Successfully");
    }

    @Override
    public ApiResponse<BookingReminderResponse> getBookingReminderById(@RequestParam("id") UUID bookingReminderId) {
        Optional<BookingReminder> bookingReminder = bookingReminderRepository.findByUuid(bookingReminderId);

        if(bookingReminder.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        BookingReminder  bk = bookingReminder.get();

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(bk,BookingReminderResponse.class));

    }

    private BookingReminder validateBookingReminder(UUID uuid){
        Optional<BookingReminder> bookingReminder = bookingReminderRepository.findByUuid(uuid);
        if(bookingReminder.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return bookingReminder.get();
    }

    @Override
    public ApiResponse<String> updateBookingReminder(@RequestParam UUID bookingReminderId,
                                                                      @RequestBody BookingReminderRequest request) {
        BookingReminder bookingReminder = validateBookingReminder(bookingReminderId);
        bookingReminder.setCustomer_id(request.getCustomer_id());
        bookingReminder.setBooking_id(request.getBooking_id());
        bookingReminder.setReminder_details(request.getReminder_details());
        bookingReminder.setStatus(request.getStatus());

        bookingReminderRepository.save(bookingReminder);
        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }

    @Override
    public ApiResponse<String> deleteBookingReminder(UUID bookingReminderId) {
        BookingReminder bookingReminder = validateBookingReminder(bookingReminderId);
        bookingReminderRepository.delete(bookingReminder);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
