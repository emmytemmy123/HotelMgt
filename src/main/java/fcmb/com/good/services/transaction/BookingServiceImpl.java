package fcmb.com.good.services.transaction;

import fcmb.com.good.exception.RecordNotFoundException;
import fcmb.com.good.mapper.Mapper;
import fcmb.com.good.model.dto.enums.AppStatus;
import fcmb.com.good.model.dto.request.transactionRequest.BookingRequest;
import fcmb.com.good.model.dto.response.othersResponse.ApiResponse;
import fcmb.com.good.model.dto.response.transactionResponse.BookingResponse;
import fcmb.com.good.model.entity.transaction.Booking;
import fcmb.com.good.model.entity.user.Customer;
import fcmb.com.good.repo.transaction.BookingRepository;
import fcmb.com.good.repo.user.CustomerRepository;
import fcmb.com.good.utills.EmailUtils;
import fcmb.com.good.utills.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private  final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final EmailUtils emailUtils;

    @Override
    /**
     * @Validate and Find the list of all bookings
     * @Validate if the List of bookings is empty otherwise return record not found
     * @return the list of bookings and a Success Message
     * * */
    public ApiResponse<List<BookingResponse>> getListOfBooking(int page, int size) {
        List<Booking> bookingList = bookingRepository.findAll(PageRequest.of(page,size)).toList();

        if(bookingList.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertList(bookingList, BookingResponse.class));
    }

    @Override
    /**
     * @Validate that customer exists otherwise return record not found
     * @Validate that room exists otherwise return record not found
     * @Validate that roomCategory exists otherwise return record not found
     * Create the booking definition and save
     * @return success message
     * * */
    public ApiResponse<String> addBooking(BookingRequest request) throws MessagingException {

        Customer existingCustomer  = customerRepository.findByUuid(request.getCustomerId())
                .orElseThrow(()->new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND));


        Booking booking = new Booking();

        booking.setNight(request.getNight());
        booking.setBookedNo(request.getBookedNo());
        booking.setBookedBy(existingCustomer.getName());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setCustomer(existingCustomer);


        bookingRepository.save(booking);

        emailUtils.sendBookingReminder(booking.getBookedBy(), "Your Booking Credentials by Hotel Management System",
                String.valueOf(booking.getCheckInDate()));

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record added Successfully");
    }


    @Override
    /**
     * @Validating and Finding the list of bookingOptional by uuid
     * @Validate if the List of bookingOptional is empty otherwise return record not found
     * Create the booking definition and get the bookingOptional by uuid
     * @return the list of bookings and a Success Message
     * * */
    public ApiResponse<BookingResponse> getBookingById(@RequestParam("id") UUID bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findByUuid(bookingId);

        if(bookingOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);

        Booking book = bookingOptional.get();

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                Mapper.convertObject(book,BookingResponse.class));

    }

    /**
     * @Validating bookingOptional by name
     * @Validate if the List of bookingOptional is empty otherwise return Duplicate Record
     * */
    private Booking validateBooking(UUID uuid){
        Optional<Booking> bookingOptional = bookingRepository.findByUuid(uuid);
        if(bookingOptional.isEmpty())
            throw new RecordNotFoundException(MessageUtil.RECORD_NOT_FOUND);
        return bookingOptional.get();
    }


    @Override
    /**
     * @validating bookingOption by uuid
     * @Validate if the List of bookingOption is empty otherwise return record not found
     * Create the booking definition and update
     * @return a Success Message
     * * */
        public ApiResponse<String> updateBooking(UUID bookingId,BookingRequest request) {

        Booking booking = validateBooking(bookingId);

        booking.setNight(request.getNight());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());

        bookingRepository.save(booking);

        return new ApiResponse<>(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Updated Successfully");
    }


    @Override
    /**
     * @validate booking by uuid
     * @Validate if booking is empty otherwise return record not found
     * @Delete booking
     * @return a Success Message
     * * */
    public ApiResponse<String> deleteBooking(UUID bookingId) {
        Booking booking = validateBooking(bookingId);
        bookingRepository.delete(booking);
        return new ApiResponse(AppStatus.SUCCESS.label, HttpStatus.OK.value(),
                "Record Deleted successfully");
    }


}
