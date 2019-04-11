package com.spada.demo;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbSeeder implements CommandLineRunner {

	private HotelRepository hotelRepository;

	public DbSeeder(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		Hotel marriot = new Hotel("Marriot", 130, new Address("Paris", "France"),
				Arrays.asList(new Review("Jhon", 8, false), new Review("Abraham", 3, true)));

		Hotel ibis = new Hotel("Ibis", 90, new Address("Bucarest", "Romania"),
				Arrays.asList(new Review("Teddy", 9, false), new Review("Mauro", 10, true)));

		Hotel sofitel = new Hotel("Sofitel", 200, new Address("Rome", "Italy"),
				Arrays.asList(new Review("Robert", 8, false)));

		Hotel nhow = new Hotel("Nhow", 400, new Address("Milan", "Italy"), Arrays.asList());

		Hotel hilton = new Hotel("hilton", 300, new Address("Venice", "Italy"),
				Arrays.asList(new Review("Teddy", 9, false), new Review("Mauro", 10, true)));

		// drop all hotels
		this.hotelRepository.deleteAll();

		// add our hotels to db
		List<Hotel> hotels = Arrays.asList(marriot, ibis, sofitel, nhow, hilton);
		this.hotelRepository.saveAll(hotels);
	}

}
