package com.spada.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.dsl.BooleanExpression;

@RestController
@RequestMapping("/hotels")
public class HotelController {
	private HotelRepository hotelRepository;

	public HotelController(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	@GetMapping("/all")
	public List<Hotel> getAll() {
		List<Hotel> hotels = this.hotelRepository.findAll();
		return hotels;
	}

	@PutMapping
	public void instert(@RequestBody Hotel hotel) {
		this.hotelRepository.insert(hotel);
	}

	@PostMapping
	public void update(@RequestBody Hotel hotel) {
		this.hotelRepository.save(hotel);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") String id) {
		this.hotelRepository.deleteById(id);
	}

	@GetMapping("/{id}")
	public Hotel getById(@PathVariable("id") String id) {
		Optional<Hotel> hotel = this.hotelRepository.findById(id);
		if (hotel.isPresent()) {
			return hotel.get();
		}
		throw new ResourcenotFoundException("ERROREtest2: " + id + " Non trovato");
	}

	@GetMapping("/price/{max}")
	public List<Hotel> getByPricePerNight(@PathVariable("max") int max) {
		return this.hotelRepository.findByPricePerNightLessThan(max);
	}

	@GetMapping("/address/{city}")
	public List<Hotel> getByCity(@PathVariable("city") String city) {
		return this.hotelRepository.findByCity(city);
	}

	@GetMapping("/country/{country}")
	public List<Hotel> getBycountry(@PathVariable("country") String country) {
		// create a query class (Qhotel)
		QHotel qHotel = new QHotel("hotel");

		// Using the query class
		BooleanExpression filterByCountry = qHotel.address.country.eq(country);

		// we can pthen pass the filters to findAll method
		return (List<Hotel>) this.hotelRepository.findAll(filterByCountry);
	}

	@GetMapping("/recommended")
	public List<Hotel> getRecommended() {
		final int maxPrice = 100;
		final int minRating = 7;
		QHotel qHotel = new QHotel("hotel");
		BooleanExpression filterByPrice = qHotel.pricePerNight.lt(maxPrice);
		BooleanExpression filterByRating = qHotel.reviews.any().rating.gt(minRating);
		return (List<Hotel>) this.hotelRepository.findAll(filterByPrice.and(filterByRating));
	}

}
