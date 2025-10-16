package services;

import entities.CarRental;
import entities.Invoice;

import java.time.Duration;

public class RentalService {
    private double pricePerHour;
    private double pricePerDay;
    private BrazilTaxService taxService;

    public RentalService(double pricePerHour, double pricePerDay, BrazilTaxService taxService) {
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.taxService = taxService;
    }

    public void processInvoice(CarRental carRental)
    {
        double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
        double hours = Math.ceil(minutes / 60);

        double basicPayment;

        if(hours > 12)
        {
            double days = Math.ceil(hours / 24);
            basicPayment = days * pricePerDay;
        }
        else
        {
            basicPayment = hours * pricePerHour;
        }

        double tax = taxService.tax(basicPayment);
        carRental.setInvoice(new Invoice(basicPayment, tax));
    }

}
