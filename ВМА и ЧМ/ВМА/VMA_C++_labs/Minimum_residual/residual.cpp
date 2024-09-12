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
std::vector<T2> operator * (T1 a, std::vector <T2>& b) {
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
std::vector <std::vector<T>> transpose(std::vector <std::vector<T>> vec) {
	int size = vec.size();
	for (int i = 0; i < size; i++) {
		for (int j = i + 1; j < size; j++) {
			std::swap(vec[i][j], vec[j][i]);
		}
	}
	return vec;
}
template <typename T>
std::vector <std::vector<T>> transpose(std::vector <T> vec) {
	int size = vec.size();
	std::vector <std::vector<T>> result(size, std::vector <T>(1));
	for (int i = 0; i < size; i++) {
		result[i][0] = vec[i];
	}
	return result;
}

template <typename T>
T scalar_product(std::vector<T> a, std::vector <T> b) {
	T result = 0;
	for (int i = 0; i < a.size(); i++) {
		result += a[i] * b[i];
	}
	return result;
}
// Транспонирования двумерного столбца в одномерный вектор
template <typename T> 
std::vector<T> transpose_to_vector(std::vector<std::vector <T>> vec) {
	int size = vec.size();
	std::vector <T> result;
	for (int i = 0; i < vec.size(); i++) {
		result.push_back(vec[i][0]);
	}
	return result;
}

int main() {
	setlocale(LC_ALL, "Russian");
	// Ввод данных
	int size = 5;
	std::vector <std::vector <long double>> x_result(size, std::vector <long double>(1));
	std::vector <std::vector <long double>> a_matrix(size, std::vector <long double>(size));
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
	// Приводим матрицу к симметричному виду
	auto b_vector_sim = matrix_product(transpose(a_matrix), b_vector);
	auto a_matrix_sim = matrix_product(transpose(a_matrix), a_matrix);
	// Точность
	long double e = 1e-5;
	x_result = b_vector_sim;
	auto x_result_new = x_result;
	// Невязка
	std::vector <std::vector <long double>> residual;
	int i = 0;
	while (true) {
		i++;
		for (int i = 0; i < size; i++) {
			residual = matrix_product(a_matrix_sim, x_result) - b_vector_sim;
			std::vector <long double> ar = transpose_to_vector(matrix_product(a_matrix_sim, residual));
			auto tau = scalar_product(ar, transpose_to_vector(residual)) / scalar_product(ar, ar);
			for (int j = 0; j < size; j++) {
				residual[j] = residual[j] * (tau);
				x_result_new[j] = x_result[j] - residual[j];
			}
		}
		if (first_matrix_norm(x_result - x_result_new) <= e) {
			x_result = x_result_new;
			break;
		}
		else {
			x_result = x_result_new;
		}
	}
	// Вывод данных
	std::cout << "x = (";
	for (int i = 0; i < size; i++) {
		std::cout << std::setw(8) << round(x_result_new[i][0] * 10000) / 10000 << std::setw(8);
	}
	std::cout << std::setw(1) << ")" << std::endl;

	std::cout << "Количество итераций: " << i << std::endl;
	std::vector <std::vector <long double>> r = matrix_product(a_matrix, x_result_new) - b_vector;
	std::cout << std::endl << "Невязка r = Ax - b:" << std::endl << "( " << std::setw(5);
	for (int i = 0; i < size; i++) {
		std::cout << std::setw(14) << r[i][0] << std::setw(14);
	}
	std::cout << std::setw(5) << ")" << std::endl;
	std::cout << std::endl << std::scientific << "Норма невязки r = " << first_matrix_norm(r) << std::endl;


	return 0;
}