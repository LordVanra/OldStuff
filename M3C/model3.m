% Visualization of J(theta, p)
% J(θ, p) = C_0 * (r*θ/2 + 0.02*p) * (1 - e^(-λ*r*θ*T)) / (2*λ*|θ|)

clear; clc;

% Parameters
C_0 = 240 * 0.25;  % 60
r = 0.05;
lambda = 0.8;      % Decay rate
T = 120;

% Define ranges
theta_range = linspace(-1, 1, 100);
p_range = linspace(0.1, 5, 100);

% Create meshgrid
[THETA, P] = meshgrid(theta_range, p_range);

% Calculate J
THETA_safe = THETA;
THETA_safe(THETA == 0) = eps;  % Avoid division by zero

exponent = -lambda .* r .* THETA_safe .* T;
J = -C_0 .* ((r .* THETA_safe / 2) + 0.02 .* P) .* (1 - exp(exponent)) ./ ((THETA_safe) .* lambda .* r);

%% Find optimal theta for each p
optimal_theta = zeros(size(p_range));
for i = 1:length(p_range)
    [~, max_idx] = max(J(i, :));
    optimal_theta(i) = theta_range(max_idx);
end

%% VISUALIZATIONS
figure('Position', [100, 100, 900, 700]);

% Heat map / Image plot with log scale coloring
% Use sign(J).*log10(abs(J)+1) to handle negative values
J_log = J;
imagesc(theta_range, p_range, J_log);
set(gca, 'YDir', 'normal');  % Flip y-axis to normal orientation
hold on;

% Draw optimal theta line
plot(optimal_theta, p_range, 'w-', 'LineWidth', 3);
plot(optimal_theta, p_range, 'k--', 'LineWidth', 1.5);

hold off;
xlabel('θ (theta)', 'FontWeight', 'bold', 'FontSize', 12);
ylabel('p (price)', 'FontWeight', 'bold', 'FontSize', 12);
title(sprintf('J(θ, p) Heat Map with Optimal θ\nC_0 = 240×0.25 = %.0f, r = %.2f, λ = %.1f, T = %d', ...
      C_0, r, lambda, T), 'FontSize', 13, 'FontWeight', 'bold');

% Colorbar with blue to red colormap
cb = colorbar;
cb.Label.String = 'sign(J) · log₁₀(|J| + 1)';
cb.Label.FontSize = 12;
cb.Label.FontWeight = 'bold';
colormap(jet);  % Blue to red through cyan-green-yellow

axis tight;
grid off;

%% STATISTICS
fprintf('=== FUNCTION STATISTICS ===\n');
fprintf('Parameters:\n');
fprintf('  C_0 = 240 × 0.25 = %.0f\n', C_0);
fprintf('  r = %.2f\n', r);
fprintf('  λ = %.1f\n', lambda);
fprintf('  T = %d\n\n', T);

fprintf('J(θ, p) Statistics:\n');
fprintf('  Min J: %.4f\n', min(J(:)));
fprintf('  Max J: %.4f\n', max(J(:)));
fprintf('  Mean J: %.4f\n', mean(J(:)));
fprintf('  Std J: %.4f\n\n', std(J(:)));

% Sample values
fprintf('Sample Values:\n');
test_points = [0.5, 2; -0.5, 2; 0.8, 5; -0.8, 8];
for i = 1:size(test_points, 1)
    theta_test = test_points(i, 1);
    p_test = test_points(i, 2);
    
    if theta_test == 0
        J_test = 0;
    else
        exp_test = -lambda * r * theta_test * T;
        J_test = C_0 * ((r * theta_test / 2) + 0.02 * p_test) * ...
                 (1 - exp(exp_test)) / (abs(theta_test) * lambda * 2);
    end
    
    fprintf('  J(θ=%.1f, p=%.1f) = %.4f\n', theta_test, p_test, J_test);
end

fprintf('\n=== OPTIMAL THETA VALUES ===\n');
fprintf('p = %.2f: θ* = %.4f\n', p_range(1), optimal_theta(1));
fprintf('p = %.2f: θ* = %.4f\n', p_range(50), optimal_theta(50));
fprintf('p = %.2f: θ* = %.4f\n', p_range(end), optimal_theta(end));