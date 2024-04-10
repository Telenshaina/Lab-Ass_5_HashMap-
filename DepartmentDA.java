import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DepartmentDA {

    private HashMap<String, Employee> employeeMap;

    public DepartmentDA(){

        try {
            Scanner departmentFile = new Scanner(new FileReader("dep.csv"));

            while (departmentFile.hasNext()){
                employeeMap = new HashMap<>();
                String departmentLineData = new String();
                departmentLineData = departmentFile.nextLine();
                String [] departmentLineDataSpecific = new String[3];
                departmentLineDataSpecific = departmentLineData.split(",");

                Department department = new Department();
                department.setDeptCode(departmentLineDataSpecific[0].trim());
                department.setDeptName(departmentLineDataSpecific[1].trim());




                readDeptEmp(department);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readDeptEmp(Department department) {
        try {
            HashMap<String, Employee> employeeMap = new HashMap<>();
            double totalSalary = 0.0;
            Scanner deptEmpFile = new Scanner(new FileReader("deptemp.csv"));

            while (deptEmpFile.hasNext()) {
                String deptEmpLineData = deptEmpFile.nextLine();
                String[] deptEmpLineDataSpecific = deptEmpLineData.split(",");

                if (department.getDeptCode().equals(deptEmpLineDataSpecific[0].trim())) {
                    String empNo = deptEmpLineDataSpecific[1].trim();
                    EmployeeDA employeeDA = new EmployeeDA(empNo);
                    Employee employee = employeeDA.getEmployeeMap().get(empNo);


                        employee.setSalary(Double.parseDouble(deptEmpLineDataSpecific[2].trim()));
                        employeeMap.put(empNo, employee);
                        totalSalary += employee.getSalary();

                }
            }

            department.setEmployeeMap(employeeMap);
            department.setDepTotalSalary(totalSalary);
            printDepartment(department);
            deptEmpFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void printDepartment(Department department) {
        System.out.println("Department code: " + department.getDeptCode());
        System.out.println("Department name: " + department.getDeptName());
        System.out.println("Department total salary: " + department.getDepTotalSalary());
        System.out.println("---------------------Details -------------------------");
        System.out.println("EmpNo\t  Employee Name\t\tSalary");
        for (Map.Entry<String, Employee> entryMap : department.getEmployeeMap().entrySet()) {
            Employee employee = entryMap.getValue();
            String employeeName = employee.getLastName() + ", " + employee.getFirstName();

            System.out.printf("%-10s%-22s%-10s%n",
                    employee.getEmpNo(), employeeName, employee.getSalary());
        }
        System.out.println();
    }

}
