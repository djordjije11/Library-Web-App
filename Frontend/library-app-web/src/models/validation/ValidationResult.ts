export default interface ValidationResult {
  isValid: boolean;
  error?: string;
}

export function getResultError(error: string): ValidationResult {
  return {
    isValid: false,
    error,
  };
}

export function getResultValid(): ValidationResult {
  return {
    isValid: true,
  };
}

export function allValid(...results: ValidationResult[]): boolean {
  return results.every((result) => result.isValid);
}
