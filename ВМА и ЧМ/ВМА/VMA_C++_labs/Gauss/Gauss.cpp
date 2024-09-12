#include <iostream>
#include <vector>
#include <iostream>
#include <fstream>
#include <iomanip>
#include <algorithm>


template <typename T>
std::vector<T> operator + (const std::vector <T>& a, const std::vector <T>& b) {
	std::vector <T> result(a.size());
	for (int i = 0; i < a.size(); i++) {
		result[i] = a[i] + b[i];
	}
	return result;
}

template <typename T>
std::vector<T> operator - (const std::vector <T>& a, const std::vector <T>& b) {
	std::vector <T> result(a.size());
	for (int i = 0; i < a.size(); i++) {
		result[i] = a[i] - b[i];
	}
	return result;
}

template <typename T1, typename T2>
std::vector<T2> operator * (T1 a, std::vector <T2> b) {
	for (int i = 0; i < b.size(); i++) {
		b[i] = b[i] * a;
	}
	return b;
}

template <typename T1, typename T2>
std::vector<T2> operator * (std::vector <T2>& b, T1 a) {
	for (int i = 0; i < b.size(); i++) {
		b[i] = b[i] * a;
	}
	return b;
}

template <typename T>
std::ostream& operator << (std::ostream& os, const std::vector <T>& a) {
	for (int i = 0; i < a.size(); i++) {
		os << std::setprecision(4) << a[i] << ' ';
	}
	return os << std::endl;
}

template <typename T>
std::vector <std::vector <T>> matrix_product(std::vector <std::vector <T>> a_matrix,
	std::vector <std::vector <T>> b_matrix) {
	int res_rows = a_matrix.size();
	int res_columns = b_matrix[0].size();
	std::vector <std::vector <T>> result(res_rows, std::vector <T>(res_columns));
	if (a_matrix[0].size() == b_matrix.size()) {
		for (int i = 0; i < res_rows; i++) {
			for (int j = 0; j < res_columns; j++) {
				for (int k = 0; k < a_matrix[0].size(); k++) {
					result[i][j] += a_matrix[i][k] * b_matrix[k][j];
				}
			}
		}
	}
	else {
		throw "incorret matrix size";
	}
	return result;
}

template <typename T>
T first_matrix_norm(std::vector <std::vector <T>> matrix) {
	int size = matrix.size();
	T sum_res = 0;
	for (int i = 0; i < size; i++) {
		T sum = 0;
		for (int j = 0; j < matrix[i].size(); j++) {
			sum += abs(matrix[i][j]);
		}
		if (sum > sum_res) {
			sum_res = sum;
		}
	}
	return sum_res;
}

template <typename T>
std::vector < std::vector <T>> gaussian_elimination(int size,
	std::vector <std::vector <T>> a_matrix,
	std::vector <std::vector <T>> b_vector, bool show_info = 0) {
	// Метод Гаусса
	std::vector <std::vector <T>> x_result(size, std::vector <T>(1));
	long double det_a = 1;
	// Прямой ход
	for (int i = 0; i < size; i++) {
		long double leading_elem = a_matrix[i][i];
		// Проверить что ведущий элемент не близок к нулю
		for (int k = i + 1; k < size; k++) {
			if (abs(leading_elem) < 0.00001) {
				a_matrix[i][i] = 0;
				std::swap(a_matrix[i], a_matrix[k]);
				std::swap(b_vector[i], b_vector[k]);
				leading_elem = a_matrix[i][i];
			}
			else {
				break;
			}
		}
		// Вычисляем определитель
		det_a *= leading_elem;
		b_vector[i] = b_vector[i] * (1 / leading_elem);
		a_matrix[i] = a_matrix[i] * (1 / leading_elem);

		for (int j = i + 1; j < size; j++) {
			b_vector[j] = b_vector[j] - a_matrix[j][i] * b_vector[i];
			a_matrix[j] = a_matrix[j] - a_matrix[j][i] * a_matrix[i];
			a_matrix[j][i] = 0;
		}
	}
	// Обратный ход
	for (int i = size - 1; i >= 0; i--) {
		x_result[i][0] = b_vector[i][0];
		for (int j = size - 1; j > i; j--) {
			x_result[i][0] -= x_result[j][0] * a_matrix[i][j];
		}
	}	
	// Вывод данных в консоль
	if (show_info) {
		std::cout << "A в верхнетреугольном виде:" << std::endl;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				std::cout << std::setw(10) << round(a_matrix[i][j] * 10000) / 10000 << std::setw(10);
			}
			std::cout << '|' << round(b_vector[i][0] * 10000) / 10000 << std::endl;
		}
		std::cout << std::endl << "x = (";
		for (int i = 0; i < size; i++) {
			std::cout << std::setw(8) << round(x_result[i][0] * 10000) / 10000 << std::setw(8);
		}
		std::cout << std::setw(1) << ")" << std::endl;
		std::cout << std::endl << "det A = " << det_a << std::endl;
	}
	return x_result;
}


int main() {
	setlocale(LC_ALL, "Russian");
	// Ввод данных
	int size = 5;
	std::vector <std::vector <long double>> x_result(size, std::vector <long double>(1));
	std::vector <std::vector <long double>> a_matrix(size, std::vector <long double> (size));
	std::vector <std::vector <long double>> b_vector(size, std::vector <long double>(1));
	std::ifstream input("input.txt");
	for (int i = 0; i < size; i++) {
		for (int j = 0; j < size; j++) {
			input >> a_matrix[i][j];
		}
	}
	for (int i = 0; i < size; i++) {
		input >> b_vector[i][0];
	}
	x_result = gaussian_elimination(size, a_matrix, b_vector, 1);
	// Обратная матрица
	std::vector <std::vector <long double>> inv_matrix(size, std::vector <long double>(size));
	for (int i = 0; i < size; i++) {
		std::vector <std::vector <long double>> e_vec(size, std::vector <long double>(1));
		e_vec[i][0] = 1;
		std::vector <std::vector <long double>> inv_column = gaussian_elimination(size, a_matrix, e_vec);
		for (int j = 0; j < size; j++) {
			inv_matrix[j][i] = inv_column[j][0];
		}
	}
	std::cout << std::endl << "A^-1 = " << std::endl;
	for (int i = 0; i < size; i++) {
		for (int j = 0; j < size; j++) {
			std::cout << std::setw(10) << round(inv_matrix[i][j] * 10000) / 10000 << std::setw(10);
		}
		std::cout << std::endl;
	}
	// Вычисление невязок
	std::vector <std::vector <long double>> r = matrix_product(a_matrix, x_result) - b_vector;
	std::cout << std::endl << "Невязка r = Ax - b:" << std::endl << "( " << std::setw(5);
	for (int i = 0; i < size; i++) {
		std::cout << std::setw(14) << r[i][0] << std::setw(14);
	}
	std::cout << std::setw(5) <<  ")" << std::endl;
	std::cout << std::endl << "Норма невязки r = " << first_matrix_norm(r) << std::endl;

	std::vector <std::vector <long double>> r_inv = matrix_product(inv_matrix, a_matrix);
	for (int i = 0; i < size; i++) {
		r_inv[i][i] -= 1;
	}
	std::cout << std::endl << "Невязка R = (A^-1)A - E:" << std::endl;
	for (int i = 0; i < size; i++) {
		for (int j = 0; j < size; j++) {
			std::cout << std::setw(14) << r_inv[i][j] << std::setw(14);
		}
		std::cout << std::endl;
	}
	std::cout << std::endl << "Норма невязки R = " << first_matrix_norm(r_inv) << std::endl;

	// Число обусловленности
	long double u = first_matrix_norm(a_matrix) * first_matrix_norm(inv_matrix);
	std::cout << std::endl << "Число обусловленности:" << std::endl << "u = " << round(u * 100) / 100;
	return 0;
}