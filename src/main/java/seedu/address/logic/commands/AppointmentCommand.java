package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Sets or updates an appointment for the person identified by the displayed index from the address book.
 */
public class AppointmentCommand extends Command {

    public static final String COMMAND_WORD = "appointment";

    public static final String COMMAND_WORD_SHORT = "appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets an appointment for the person identified by the index number used in the displayed person list.\n"
            + "Parameters: NRIC, app/APPOINTMENT (in the format DD-MM-YYYY HH:MM)\n"
            + "Example: " + COMMAND_WORD + " S1234567A app/25-12-2024 14:30";

    private final Nric nric;
    private final String appointmentString; // Store appointment string to convert to LocalDateTime

    /**
     * Constructor to create an AppointmentCommand.
     *
     * @param targetNric NRIC of the person to set the appointment for.
     * @param appointmentString String of the appointment in the format a/DD-MM-YYYY HH:MM.
     */
    public AppointmentCommand(Nric targetNric, String appointmentString) {
        this.nric = targetNric;
        this.appointmentString = appointmentString;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getAddressBook().getPersonList();

        Optional<Person> personWithMatchingNric = lastShownList.stream()
                .filter(person -> nric.equals(person.getNric()))
                .findFirst();

        if (personWithMatchingNric.isPresent()) {
            Person personToEdit = personWithMatchingNric.get();
            Person editedPerson = new Person(
                    personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getNric(),
                    personToEdit.getAddress(), personToEdit.getTriage(), personToEdit.getRemark(),
                    personToEdit.getTags(), new Appointment(this.appointmentString), personToEdit.getLogEntries());
            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult("Set appointment for " + personToEdit.getName() + " on " + this.appointmentString);
        } else {
            throw new CommandException(Messages.MESSAGE_NO_PERSON_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AppointmentCommand)) {
            return false;
        }

        AppointmentCommand otherCommand = (AppointmentCommand) other;
        return nric.equals(otherCommand.nric)
                && appointmentString.equals(otherCommand.appointmentString);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                //.add("targetIndex", index)
                .add("targetNric", nric)
                .add("appointmentString", appointmentString)
                .toString();
    }
}
