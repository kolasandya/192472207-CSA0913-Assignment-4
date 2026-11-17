# Task 2 - Hospital Outpatient Appointment Desk Models

## Task 2.1 - Event Model Table
| Event Source | Event Object Generated | Listener Interface | Action Taken |
|---|---|---|---|
| `JComboBox` (Department) | `ItemEvent` / `ActionEvent` | `ItemListener` | Filters and updates the doctor dropdown list for the selected department. |
| `JButton` (Book Appointment) | `ActionEvent` | `ActionListener` | Validates patient ID, time slot availability, and writes appointment to DB. |
| `JFrame` (Window Close) | `WindowEvent` | `WindowListener` / `WindowAdapter` | Prompts confirmation dialog if booking is in progress before closing. |

## Task 2.2 - Component Hierarchy
- `JFrame` (`BorderLayout`)
  - NORTH: `JPanel` (`FlowLayout`) - Hospital Header Title Label
  - CENTER: `JPanel` (`GridLayout` 4x2)
    - `JLabel` ("Patient ID:") & `JTextField` (Patient ID Input)
    - `JLabel` ("Department:") & `JComboBox` (Cardiology, Orthopedics, Pediatrics)
    - `JLabel` ("Doctor:") & `JComboBox` (Filtered Doctors)
    - `JLabel` ("Time Slot:") & `JComboBox` (Available Slots)
  - SOUTH: `JPanel` (`FlowLayout`) - `JButton` ("Book Appointment"), `JButton` ("Cancel")
