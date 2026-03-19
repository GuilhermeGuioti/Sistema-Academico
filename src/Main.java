import entity.*;
import service.*;
import java.util.Scanner;
import java.util.List;
//teste
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            StudentService.loadAll();
            CourseService.loadAll();
            EnrollmentService.loadAll();
        } catch (Exception e) {
            System.err.println("Aviso: Não foi possível carregar os dados iniciais.");
        }

        int option = -1;

        while (option != 0) {
            System.out.println("\n=== SISTEMA ACADÊMICO ===");
            System.out.println("1. Gerenciar Alunos");
            System.out.println("2. Gerenciar Disciplinas");
            System.out.println("3. Matrículas e Notas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1 -> studentMenu();
                    case 2 -> courseMenu();
                    case 3 -> enrollmentMenu();
                    case 0 -> System.out.println("Encerrando sistema...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.err.println("\nERRO: " + e.getMessage());
            }
        }
    }

    private static void studentMenu() {
        System.out.println("\n--- GERENCIAR ALUNOS ---");
        System.out.println("[1] Cadastrar Aluno");
        System.out.println("[2] Listar Todos");
        System.out.println("[3] Perfil/Consultar");
        System.out.println("[4] Editar Aluno");
        System.out.println("[5] Excluir Aluno");
        System.out.println("[0] Voltar");
        System.out.print("Escolha: ");
        int op = Integer.parseInt(scanner.nextLine());

        try {
            if (op == 1) {
                System.out.print("Nome do Aluno: ");
                String name = scanner.nextLine();

                Student s = new Student();
                s.setName(name);
                StudentService.save(s);

                System.out.println("Sucesso: RA " + s.getId());

            } else if (op == 2) {
                StudentService.findAll().forEach(s ->
                        System.out.println("RA: " + s.getId() + " | Nome: " + s.getName()));

            } else if (op == 3) {
                System.out.print("RA para consulta: ");
                int ra = Integer.parseInt(scanner.nextLine());

                Student s = StudentService.findById(ra);
                System.out.println("\n=== PERFIL: " + s.getName() + " ===");

                try {
                    List<Enrollment> m = EnrollmentService.findByStudent(ra);
                    m.forEach(e -> {
                        Course c = CourseService.findById(e.getIdCourse());
                        System.out.println("- " + c.getName() + " (" + e.getStatus() + ")");
                    });
                } catch (Exception e) { System.out.println("[Sem matrículas]"); }

            } else if (op == 4) {
                System.out.print("RA do Aluno: ");
                int ra = Integer.parseInt(scanner.nextLine());

                Student s = StudentService.findById(ra);
                System.out.print("Novo nome (atual: " + s.getName() + "): ");
                String name = scanner.nextLine();

                Student studentUpdated = new Student();
                studentUpdated.setId(ra);
                studentUpdated.setName(name);

                StudentService.update(studentUpdated);

                System.out.println("Nome atualizado para " + name);

            } else if (op == 5) {
                System.out.print("RA para excluir: ");
                int ra = Integer.parseInt(scanner.nextLine());
                StudentService.delete(ra);
                System.out.println("Aluno removido!");
            }
        } catch (Exception e) { System.err.println("\nERRO ALUNO: " + e.getMessage()); }
    }

    private static void courseMenu() {
        System.out.println("\n--- GERENCIAR DISCIPLINAS ---");
        System.out.println("[1] Cadastrar Disciplina");
        System.out.println("[2] Listar Todos");
        System.out.println("[3] Ver Alunos da Disciplina");
        System.out.println("[4] Editar Disciplina");
        System.out.println("[5] Excluir Disciplina");
        System.out.println("[0] Voltar");
        System.out.print("Escolha: ");
        int op = Integer.parseInt(scanner.nextLine());

        try {
            if (op == 1) {
                System.out.print("Nome: "); String name = scanner.nextLine();
                System.out.print("Carga H.: "); double workload = Double.parseDouble(scanner.nextLine());
                System.out.print("Período: "); String period = scanner.nextLine();

                Course course = new Course();
                course.setName(name);
                course.setWorkload(workload);
                course.setPeriod(period);

                CourseService.save(course);
                System.out.println("Disciplina cadastrada!");

            } else if (op == 2) {
                CourseService.findAll().forEach(c -> System.out.println(c.getId() + " - " + c.getName()));

            } else if (op == 3) {
                System.out.print("ID da Disciplina: ");
                int idC = Integer.parseInt(scanner.nextLine());
                Course c = CourseService.findById(idC);
                System.out.println("\n=== ALUNOS EM: " + c.getName() + " ===");
                try {
                    List<Enrollment> matriculas = EnrollmentService.findByCourse(idC);
                    for (Enrollment e : matriculas) {
                        Student s = StudentService.findById(e.getIdStudent());
                        System.out.println("- " + s.getName() + " (RA: " + s.getId() + ")");
                    }
                } catch (Exception e) { System.out.println("[Nenhum aluno matriculado]"); }

            } else if (op == 4) {
                System.out.print("ID da Disciplina: ");
                int idC = Integer.parseInt(scanner.nextLine());

                Course c = CourseService.findById(idC);

                System.out.print("Novo nome (" + c.getName() + "): ");
                String name = scanner.nextLine();

                System.out.print("Nova Carga Horária (" + c.getWorkload() + "): ");
                String wordload = scanner.nextLine();

                System.out.print("Novo Período (" + c.getPeriod() + "): ");
                String period = scanner.nextLine();

                Course courseUpdated = new Course();
                courseUpdated.setId(idC);
                courseUpdated.setName(name);
                courseUpdated.setPeriod(period);

                CourseService.update(courseUpdated);

                System.out.println("Disciplina atualizada!");

            } else if (op == 5) {
                System.out.print("ID para excluir: ");
                int idC = Integer.parseInt(scanner.nextLine());
                CourseService.delete(idC);
                System.out.println("Disciplina removida!");
            }
        } catch (Exception e) { System.err.println("\nERRO DISCIPLINA: " + e.getMessage()); }
    }

    private static void enrollmentMenu() {
        System.out.println("\n--- GERENCIAR MATRÍCULAS ---");
        System.out.println("[1] Matricular Aluno");
        System.out.println("[2] Lançar Nota");
        System.out.println("[3] Boletim/Média");
        System.out.println("[4] Desmatricular Aluno");
        System.out.println("[0] Voltar");
        System.out.print("Escolha: ");
        int op = Integer.parseInt(scanner.nextLine());

        try {
            if (op == 1) {
                System.out.print("RA: "); int ra = Integer.parseInt(scanner.nextLine());
                System.out.print("ID Disciplina: "); int idC = Integer.parseInt(scanner.nextLine());

                Enrollment enrollment = new Enrollment();
                enrollment.setIdStudent(ra);
                enrollment.setIdCourse(idC);

                EnrollmentService.save(enrollment);
                System.out.println("Matriculado!");

            } else if (op == 2) {
                System.out.print("RA: "); int ra = Integer.parseInt(scanner.nextLine());
                System.out.print("ID Disciplina: "); int idC = Integer.parseInt(scanner.nextLine());
                System.out.print("Prova (1/2): "); int ex = Integer.parseInt(scanner.nextLine());
                System.out.print("Nota: "); double g = Double.parseDouble(scanner.nextLine());
                EnrollmentService.updateGrade(ra, idC, ex, g);
                System.out.println("Nota salva!");

            } else if (op == 3) {
                System.out.print("RA: "); int ra = Integer.parseInt(scanner.nextLine());
                Student s = StudentService.findById(ra);
                System.out.println("\n=== BOLETIM: " + s.getName() + " ===");
                EnrollmentService.findByStudent(ra).forEach(e -> {
                    Course c = CourseService.findById(e.getIdCourse());
                    System.out.printf("%-15s | Média: %.1f | Status: %s%n", c.getName(), e.getAverage(), e.getStatus());
                });

            } else if (op == 4) {
                System.out.print("RA: "); int ra = Integer.parseInt(scanner.nextLine());
                System.out.print("ID Disciplina: "); int idC = Integer.parseInt(scanner.nextLine());
                EnrollmentService.delete(ra, idC);
                System.out.println("Aluno desmatriculado com sucesso!");
            }
        } catch (Exception e) { System.err.println("\nERRO MATRÍCULA: " + e.getMessage()); }
    }
}