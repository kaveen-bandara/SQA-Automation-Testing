import { test, expect } from '@playwright/test';

test ('verify login form', async ({ page }) => {
    await page.goto('https://demo.applitools.com/');
    await expect(page).toHaveTitle(/Applitools/);
});

test ('verify successful login', async ({ page }) => {
    await page.goto('https://demo.applitools.com/');
    await page.locator('#username').fill('kaveen'); // Find locator by id
    await page.locator('[placeholder="Enter your password"]').fill('testing123'); // Find locator by attribute
    await page.locator('#log-in').click();
    await expect(page).toHaveURL('https://demo.applitools.com/app.html');
    await page.waitForTimeout(3000);
});
