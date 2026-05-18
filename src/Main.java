import entity.*;
import service.*;
import java.util.Scanner;
import java.util.List;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            StudentService.loadAll();
            CourseService.loadAll();
            EnrollmentService.loadAll();
            System.out.println("[SISTEMA] Base de dados carregada com sucesso.");
        } catch (Exception e) {
            System.err.println("[AVISO] Não foi possível carregar os dados iniciais. O sistema iniciará vazio.");
        }

        int option = -1;

        while (option != 0) {
            System.out.println("\n=========================================");
            System.out.println("           SISTEMA ACADÊMICO             ");
            System.out.println("=========================================");
            System.out.println("1. Gerenciar Alunos");
            System.out.println("2. Gerenciar Disciplinas");
            System.out.println("3. Matrículas e Notas");
            System.out.println("0. Sair");
            System.out.println("-----------------------------------------");
            System.out.print("Escolha uma opção: ");

            try {
                option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1 -> studentMenu();
                    case 2 -> courseMenu();
                    case 3 -> enrollmentMenu();
                    case 0 -> System.out.println("\n[INFO] Encerrando o sistema... Até logo!");
                    default -> System.out.println("[AVISO] Opção inválida! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.err.println("\n[ERRO] Entrada inválida! Por favor, digite apenas números.");
            } catch (Exception e) {
                System.err.println("\n[ERRO CRÍTICO] " + e.getMessage());
            }
        }
    }

    private static void studentMenu() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n--- MENU: GERENCIAR ALUNOS ---");
            System.out.println("[1] Cadastrar Aluno");
            System.out.println("[2] Listar Todos");
            System.out.println("[3] Perfil/Consultar");
            System.out.println("[4] Editar Aluno");
            System.out.println("[5] Excluir Aluno");
            System.out.println("[0] Voltar ao Menu Principal");
            System.out.print("Escolha: ");

            try {
                op = Integer.parseInt(scanner.nextLine());

                if (op == 1) {
                    System.out.print("Nome do Aluno: ");
                    String name = scanner.nextLine();

                    Student s = new Student();
                    s.setName(name);
                    StudentService.save(s);

                    System.out.println("[SUCESSO] Aluno '" + s.getName() + "' cadastrado com o RA: " + s.getId());

                } else if (op == 2) {
                    System.out.println("\n--- LISTAGEM DE ALUNOS ---");
                    List<Student> all = StudentService.findAll();
                    if (all.isEmpty()) {
                        System.out.println("[INFO] Nenhum aluno cadastrado.");
                    } else {
                        all.forEach(s ->
                                System.out.println("RA: " + s.getId() + " | Nome: " + s.getName() + " | Carga: " + s.getWorkload() + "h")
                        );
                    }

                } else if (op == 3) {
                    System.out.print("Digite o RA para consulta: ");
                    int ra = Integer.parseInt(scanner.nextLine());

                    Student s = StudentService.findById(ra);
                    System.out.println("\n=== PERFIL DO ALUNO ===");
                    System.out.println("RA: " + s.getId());
                    System.out.println("Nome: " + s.getName());
                    System.out.println("Carga Horária no Semestre: " + s.getWorkload() + "h / 300h");
                    System.out.println("Disciplinas:");

                    List<Enrollment> m = EnrollmentService.findByStudent(ra);
                    if (m.isEmpty()) {
                        System.out.println("  [Nenhuma matrícula encontrada]");
                    } else {
                        m.forEach(e -> {
                            Course c = CourseService.findById(e.getIdCourse());
                            System.out.println("  - " + c.getName() + " [Status: " + e.getStatus() + "]");
                        });
                    }

                } else if (op == 4) {
                    System.out.print("RA do Aluno que deseja editar: ");
                    int ra = Integer.parseInt(scanner.nextLine());

                    Student s = StudentService.findById(ra);
                    System.out.print("Novo nome (Atual: " + s.getName() + "): ");
                    String name = scanner.nextLine();

                    Student studentUpdated = new Student();
                    studentUpdated.setId(ra);
                    studentUpdated.setName(name);

                    StudentService.update(studentUpdated);
                    System.out.println("[SUCESSO] O aluno com RA " + ra + " agora se chama: " + name);

                } else if (op == 5) {
                    System.out.print("RA do Aluno que deseja excluir: ");
                    int ra = Integer.parseInt(scanner.nextLine());

                    // Buscamos o nome antes de deletar para uma mensagem mais completa
                    Student s = StudentService.findById(ra);
                    String oldName = s.getName();

                    StudentService.delete(ra);
                    System.out.println("[SUCESSO] Aluno '" + oldName + "' (RA: " + ra + ") removido do sistema.");

                } else if (op != 0) {
                    System.out.println("[AVISO] Opção inválida!");
                }

            } catch (NumberFormatException e) {
                System.err.println("\n[ERRO] Entrada inválida! Digite apenas números.");
            } catch (Exception e) {
                System.err.println("\n[ERRO ALUNO] " + e.getMessage());
            }
        }
    }

    private static void courseMenu() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n--- MENU: GERENCIAR DISCIPLINAS ---");
            System.out.println("[1] Cadastrar Disciplina");
            System.out.println("[2] Listar Todas");
            System.out.println("[3] Ver Alunos Matriculados");
            System.out.println("[4] Editar Disciplina");
            System.out.println("[5] Excluir Disciplina");
            System.out.println("[0] Voltar ao Menu Principal");
            System.out.print("Escolha: ");

            try {
                op = Integer.parseInt(scanner.nextLine());

                if (op == 1) {
                    System.out.print("Nome da Disciplina: ");
                    String name = scanner.nextLine();
                    System.out.print("Carga Horária: ");
                    double workload = Double.parseDouble(scanner.nextLine());
                    System.out.print("Período (Ex: 1º Semestre): ");
                    String period = scanner.nextLine();

                    Course course = new Course();
                    course.setName(name);
                    course.setWorkload(workload);
                    course.setPeriod(period);

                    CourseService.save(course);
                    System.out.println("[SUCESSO] Disciplina '" + name + "' cadastrada com ID: " + course.getId());

                } else if (op == 2) {
                    System.out.println("\n--- LISTAGEM DE DISCIPLINAS ---");
                    List<Course> all = CourseService.findAll();
                    if (all.isEmpty()) {
                        System.out.println("[INFO] Nenhuma disciplina cadastrada.");
                    } else {
                        all.forEach(c ->
                                System.out.println("ID: " + c.getId() + " | Nome: " + c.getName() + " | Carga: " + c.getWorkload() + "h")
                        );
                    }

                } else if (op == 3) {
                    System.out.print("Digite o ID da Disciplina: ");
                    int idC = Integer.parseInt(scanner.nextLine());

                    Course c = CourseService.findById(idC);
                    System.out.println("\n=== ALUNOS MATRICULADOS EM: " + c.getName() + " ===");

                    List<Enrollment> matriculas = EnrollmentService.findByCourse(idC);
                    if (matriculas.isEmpty()) {
                        System.out.println("  [Nenhum aluno matriculado nesta disciplina]");
                    } else {
                        for (Enrollment e : matriculas) {
                            Student s = StudentService.findById(e.getIdStudent());
                            System.out.println("- " + s.getName() + " (RA: " + s.getId() + ")");
                        }
                    }

                } else if (op == 4) {
                    System.out.print("ID da Disciplina que deseja editar: ");
                    int idC = Integer.parseInt(scanner.nextLine());

                    Course c = CourseService.findById(idC);

                    System.out.print("Novo nome (Atual: " + c.getName() + "): ");
                    String name = scanner.nextLine();

                    System.out.print("Nova Carga Horária (Atual: " + c.getWorkload() + "): ");
                    String workloadStr = scanner.nextLine();

                    System.out.print("Novo Período (Atual: " + c.getPeriod() + "): ");
                    String period = scanner.nextLine();

                    Course courseUpdated = new Course();
                    courseUpdated.setId(idC);
                    courseUpdated.setName(name.isBlank() ? c.getName() : name);
                    courseUpdated.setWorkload(workloadStr.isBlank() ? c.getWorkload() : Double.parseDouble(workloadStr));
                    courseUpdated.setPeriod(period.isBlank() ? c.getPeriod() : period);

                    CourseService.update(courseUpdated);
                    System.out.println("[SUCESSO] Dados da disciplina ID " + idC + " (" + courseUpdated.getName() + ") foram atualizados.");

                } else if (op == 5) {
                    System.out.print("ID da Disciplina que deseja excluir: ");
                    int idC = Integer.parseInt(scanner.nextLine());

                    Course c = CourseService.findById(idC);
                    String oldName = c.getName();

                    CourseService.delete(idC);
                    System.out.println("[SUCESSO] Disciplina '" + oldName + "' (ID: " + idC + ") removida do sistema.");

                } else if (op != 0) {
                    System.out.println("[AVISO] Opção inválida!");
                }

            } catch (NumberFormatException e) {
                System.err.println("\n[ERRO] Entrada inválida! Digite apenas números.");
            } catch (Exception e) {
                System.err.println("\n[ERRO DISCIPLINA] " + e.getMessage());
            }
        }
    }

    private static void enrollmentMenu() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n--- MENU: MATRÍCULAS E NOTAS ---");
            System.out.println("[1] Matricular Aluno em Disciplina");
            System.out.println("[2] Lançar Nota de Prova");
            System.out.println("[3] Ver Boletim do Aluno");
            System.out.println("[4] Cancelar Matrícula (Desmatricular)");
            System.out.println("[5] Encerrar Semestre (Zerar Carga Horária)");
            System.out.println("[0] Voltar ao Menu Principal");
            System.out.print("Escolha: ");

            try {
                op = Integer.parseInt(scanner.nextLine());

                if (op == 1) {
                    System.out.print("RA do Aluno: ");
                    int ra = Integer.parseInt(scanner.nextLine());
                    System.out.print("ID da Disciplina: ");
                    int idC = Integer.parseInt(scanner.nextLine());

                    Enrollment enrollment = new Enrollment();
                    enrollment.setIdStudent(ra);
                    enrollment.setIdCourse(idC);

                    EnrollmentService.save(enrollment);

                    Student student = StudentService.findById(ra);
                    Course course = CourseService.findById(idC);

                    System.out.println("[SUCESSO] Aluno '" + student.getName() + "' matriculado em '" + course.getName() + "'.");

                } else if (op == 2) {
                    System.out.print("RA do Aluno: ");
                    int ra = Integer.parseInt(scanner.nextLine());
                    System.out.print("ID da Disciplina: ");
                    int idC = Integer.parseInt(scanner.nextLine());
                    System.out.print("Qual prova? (1 ou 2): ");
                    int ex = Integer.parseInt(scanner.nextLine());
                    System.out.print("Valor da Nota: ");
                    double g = Double.parseDouble(scanner.nextLine());

                    EnrollmentService.updateGrade(ra, idC, ex, g);
                    System.out.println("[SUCESSO] Nota " + g + " lançada para a P" + ex + " do aluno (RA: " + ra + ") na disciplina ID: " + idC);

                } else if (op == 3) {
                    System.out.print("RA do Aluno para o boletim: ");
                    int ra = Integer.parseInt(scanner.nextLine());

                    Student s = StudentService.findById(ra);
                    System.out.println("\n==========================================================================");
                    System.out.println("BOLETIM ACADÊMICO: " + s.getName().toUpperCase() + " (RA: " + ra + ")");
                    System.out.println("--------------------------------------------------------------------------");
                    System.out.printf("%-20s | %-5s | %-5s | %-5s | %-10s%n", "DISCIPLINA", "P1", "P2", "MED", "STATUS");
                    System.out.println("--------------------------------------------------------------------------");

                    List<Enrollment> boletim = EnrollmentService.findByStudent(ra);
                    if (boletim.isEmpty()) {
                        System.out.println("  [Nenhuma matrícula encontrada]");
                    } else {
                        boletim.forEach(e -> {
                            Course c = CourseService.findById(e.getIdCourse());
                            System.out.printf("%-20s | %-5.1f | %-5.1f | %-5.1f | %-10s%n",
                                    (c.getName().length() > 20 ? c.getName().substring(0, 17) + "..." : c.getName()),
                                    e.getGrade1(),
                                    e.getGrade2(),
                                    e.getAverage(),
                                    e.getStatus());
                        });
                    }
                    System.out.println("==========================================================================");

                } else if (op == 4) {
                    System.out.print("RA do Aluno: ");
                    int ra = Integer.parseInt(scanner.nextLine());
                    System.out.print("ID da Disciplina: ");
                    int idC = Integer.parseInt(scanner.nextLine());

                    EnrollmentService.delete(ra, idC);
                    System.out.println("[SUCESSO] Matrícula removida: O aluno (RA: " + ra + ") não faz mais parte da disciplina (ID: " + idC + ").");

                } else if (op == 5) {
                    System.out.print("Confirma o encerramento do semestre? Isso zerará a carga horária de todos os alunos. (s/n): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("s")) {
                        StudentService.resetAllWorkloads();
                        System.out.println("[SUCESSO] Semestre encerrado. Carga horária de todos os alunos foi zerada.");
                    } else {
                        System.out.println("[CANCELADO] Operação cancelada.");
                    }

                } else if (op != 0) {
                    System.out.println("[AVISO] Opção inválida!");
                }

            } catch (NumberFormatException e) {
                System.err.println("\n[ERRO] Entrada inválida! Digite apenas números.");
            } catch (Exception e) {
                System.err.println("\n[ERRO MATRÍCULA] " + e.getMessage());
            }
        }
    }
}