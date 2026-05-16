import { test, expect } from '@playwright/test';
// const { test, expect } = require('@playwright/test');

test ('verify calorie calculator', async ({ page }) => {
    await page.goto('https://www.calculator.net/calorie-calculator.html');
    await expect(page).toHaveTitle(/Calorie Calculator/);
});