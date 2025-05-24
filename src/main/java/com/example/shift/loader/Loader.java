package com.example.shift.loader;

import com.example.shift.entity.*;
import com.example.shift.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Loader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoomRepository roomRepository;
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        loadRoles();
        loadUsers();
        loadRooms();
        loadTeachers();
        loadStudentsAndGroups();
    }

    private void loadRoles() {
        if (roleRepository.count() == 0) {
            System.out.println("Loading initial roles...");
            Role adminRole = Role.builder().name("ADMIN").build();
            Role userRole = Role.builder().name("USER").build();
            roleRepository.saveAll(List.of(adminRole, userRole));
            System.out.println("Roles created.");
        } else {
            System.out.println("Roles already exist. Skipping initial role loading.");
        }
    }

    private void loadUsers() {
        Optional<Users> existingAdmin = userRepository.findByPhone("+998901234567");

        if (existingAdmin.isEmpty()) {
            System.out.println("Loading initial users...");

            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found! Please ensure it's created."));
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("USER role not found! Please ensure it's created."));

            Users admin = Users.builder()
                    .phone("+998901234567")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin_password"))
                    .fullName("Main Administrator")
                    .roles(List.of(adminRole))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            userRepository.save(admin);
            System.out.println("Admin user created with phone: " + admin.getPhone());

            Users regularUser = Users.builder()
                    .phone("+998917654321")
                    .email("user@example.com")
                    .password(passwordEncoder.encode("user_password"))
                    .fullName("Regular User")
                    .roles(List.of(userRole))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            userRepository.save(regularUser);
            System.out.println("Regular user created with phone: " + regularUser.getPhone());

            System.out.println("Initial users loaded.");
        } else {
            System.out.println("Users already exist. Skipping initial user loading.");
        }
    }

    private void loadRooms() {
        if (roomRepository.count() == 0) {
            System.out.println("Loading initial rooms...");
            Room room1 = Room.builder().name("Room 101").build();
            Room room2 = Room.builder().name("Room 202").build();
            Room room3 = Room.builder().name("Gym Hall").build();
            roomRepository.saveAll(List.of(room1, room2, room3));
            System.out.println("Rooms created.");
        } else {
            System.out.println("Rooms already exist. Skipping initial room loading.");
        }
    }

    private void loadTeachers() {
        if (teacherRepository.count() == 0) {
            System.out.println("Loading initial teachers...");
            Teacher teacher1 = Teacher.builder()
                    .name("John Doe")
                    .gender("Male")
                    .phone("+998901112233")
                    .dataBirthday(LocalDate.of(1985, 4, 10))
                    .build();

            Teacher teacher2 = Teacher.builder()
                    .name("Jane Smith")
                    .gender("Female")
                    .phone("+998914445566")
                    .dataBirthday(LocalDate.of(1992, 11, 25))
                    .build();
            teacherRepository.saveAll(List.of(teacher1, teacher2));
            System.out.println("Teachers created.");
        } else {
            System.out.println("Teachers already exist. Skipping initial teacher loading.");
        }
    }

    private void loadStudentsAndGroups() {
        if (studentRepository.count() == 0 || groupRepository.count() == 0) {
            System.out.println("Loading initial students and groups...");

            List<Room> rooms = roomRepository.findAll();
            List<Teacher> teachers = teacherRepository.findAll();

            if (rooms.isEmpty() || teachers.isEmpty()) {
                System.err.println("Cannot load students and groups: Rooms or Teachers are empty. Please ensure they are loaded first.");
                return;
            }

            Room room101 = rooms.get(0);
            Room room202 = rooms.size() > 1 ? rooms.get(1) : rooms.get(0);

            Teacher teacherJohn = teachers.get(0);
            Teacher teacherJane = teachers.size() > 1 ? teachers.get(1) : teachers.get(0);


            Student studentA = Student.builder()
                    .fullName("Alice Johnson")
                    .dataOfBirthday(LocalDate.of(2007, 2, 15))
                    .email("alice.j@example.com")
                    .phone("+998971234567")
                    .gender("Female")
                    .isActive(true)
                    .isDeleted(false)
                    .comment("First semester")
                    .build();

            Student studentB = Student.builder()
                    .fullName("Bob Williams")
                    .dataOfBirthday(LocalDate.of(2006, 8, 30))
                    .email("bob.w@example.com")
                    .phone("+998987654321")
                    .gender("Male")
                    .isActive(true)
                    .isDeleted(false)
                    .comment("Needs extra help")
                    .build();

            Student studentC = Student.builder()
                    .fullName("Charlie Brown")
                    .dataOfBirthday(LocalDate.of(2007, 11, 5))
                    .email("charlie.b@example.com")
                    .phone("+998991112233")
                    .gender("Male")
                    .isActive(true)
                    .isDeleted(false)
                    .comment("Advanced student")
                    .build();
            studentRepository.saveAll(List.of(studentA, studentB, studentC));
            System.out.println("Students created.");

            Group mathGroup = Group.builder()
                    .name("Math Group A")
                    .course("Mathematics")
                    .price(500.00)
                    .lessonStartDate(LocalDate.of(2025, 9, 1))
                    .startTime(LocalTime.of(9, 0))
                    .endTime(LocalTime.of(10, 30))
                    .days("MON,WED,FRI")
                    .room(room101)
                    .teacher(teacherJohn)
                    .build();

            Group physicsGroup = Group.builder()
                    .name("Physics Group B")
                    .course("Physics")
                    .price(600.00)
                    .lessonStartDate(LocalDate.of(2025, 9, 2))
                    .startTime(LocalTime.of(13, 0))
                    .endTime(LocalTime.of(14, 30))
                    .days("TUE,THU")
                    .room(room202)
                    .teacher(teacherJane)
                    .build();

            Set<Student> studentsInMathGroup = new HashSet<>();
            studentsInMathGroup.add(studentA);
            studentsInMathGroup.add(studentB);
            mathGroup.setStudents(studentsInMathGroup);

            Set<Student> studentsInPhysicsGroup = new HashSet<>();
            studentsInPhysicsGroup.add(studentB);
            studentsInPhysicsGroup.add(studentC);
            physicsGroup.setStudents(studentsInPhysicsGroup);

            groupRepository.saveAll(List.of(mathGroup, physicsGroup));
            System.out.println("Groups created and students assigned.");

            System.out.println("Initial students and groups loaded.");
        } else {
            System.out.println("Students and groups already exist. Skipping initial loading.");
        }
    }
}